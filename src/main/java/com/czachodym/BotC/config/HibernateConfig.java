package com.czachodym.BotC.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.StandardBasicTypes;

public class HibernateConfig implements FunctionContributor {
    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        SqmFunctionRegistry functionRegistry = functionContributions.getFunctionRegistry();

        functionRegistry.register(
                "group_concat",
                new StandardSQLFunction("group_concat", StandardBasicTypes.STRING)
        );
    }
}
