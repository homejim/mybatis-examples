package com.homejim.mybatis.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * 分页插件
 *
 * @author homejim
 * @since 2019-09-27 12:24
 */
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
})
@Slf4j
public class PageInterceptor implements Interceptor {

    private static int MAPPEDSTATEMENT_INDEX = 0;

    private static int PARAMETER_INDEX = 1;

    private static int ROWBOUNDS_INDEX = 2;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // 从 Invocation 中获取参数
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) queryArgs[MAPPEDSTATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];

        PageUtil.Page paingParam = PageUtil.getPaingParam();
        if (paingParam != null) {

            // 构造新的 sql, select xxx from xxx where yyy limit offset,limit
            final BoundSql boundSql = ms.getBoundSql(parameter);
            String pagingSql = getPagingSql(boundSql.getSql(), paingParam.getOffset(), paingParam.getLimit());

            // 设置新的 MappedStatement
            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), pagingSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            MappedStatement mappedStatement = newMappedStatement(ms, newBoundSql);
            queryArgs[MAPPEDSTATEMENT_INDEX] = mappedStatement;

            // 重置 RowBound
            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
        }
        Object result = invocation.proceed();
        PageUtil.removePagingParam();
        return result;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 创建 MappedStatement
     * @param ms
     * @param newBoundSql
     * @return
     */
    private MappedStatement newMappedStatement(MappedStatement ms, BoundSql newBoundSql) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(),
                new BoundSqlSqlSource(newBoundSql), ms.getSqlCommandType());
        builder.useCache(ms.isUseCache());
        builder.cache(ms.getCache());
        builder.databaseId(ms.getDatabaseId());
        builder.fetchSize(ms.getFetchSize());
        builder.flushCacheRequired(ms.isFlushCacheRequired());

        builder.keyColumn(delimitedArrayToString(ms.getKeyColumns()));
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(delimitedArrayToString(ms.getKeyProperties()));

        builder.lang(ms.getLang());
        builder.resource(ms.getResource());

        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultOrdered(ms.isResultOrdered());
        builder.resultSets(delimitedArrayToString(ms.getResultSets()));
        builder.resultSetType(ms.getResultSetType());

        builder.timeout(ms.getTimeout());
        builder.statementType(ms.getStatementType());
        return builder.build();
    }



    public String getPagingSql(String sql, int offset, int limit) {
        StringBuilder result = new StringBuilder(sql.length() + 100);
        result.append(sql).append(" limit ");

        if (offset > 0) {
            result.append(offset).append(",").append(limit);
        }else{
            result.append(limit);
        }
        return result.toString();
    }

    public String delimitedArrayToString(String[] array) {
        String result = "";
        if (array == null || array.length == 0) {
            return result;
        }
        for (int i = 0; i < array.length; i++) {
            result += array[i];
            if (i != array.length - 1) {
                result += ",";
            }
        }
        return result;
    }
}
