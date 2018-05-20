package com.example.demo.config.ds.sharding.write;

import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.example.demo.base.DataSourceNames;
import com.example.demo.config.ds.sharding.ShardingDataSourceFactoryBean;
import com.example.demo.config.ds.sharding.ShardingTableRule;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 写库分离
 */
public class RecordShardingDataSourceFactoryBean extends ShardingDataSourceFactoryBean {

    ShardingInfo shardingInfo = ShardingInfo.getInstance();

    @Override
    protected ShardingRule createShardingRule() {
        DataSourceRule dataSourceRule = new DataSourceRule(createDataSourceMap());
        ShardingTableRule recordTable = new ShardingTableRule(DataSourceNames.DB_SHARDING_TABLE_BASE_NAME);
        //ShardingTableRule recordTable2 = new ShardingTableRule("t_redpacket");

        TableRule recordTableRule = TableRule.builder(recordTable.getLogicTableName()).actualTables(recordTable.getActualTableNames()).dataSourceRule(dataSourceRule).build();
        //TableRule recordTableRule2 = TableRule.builder(recordTable2.getLogicTableName()).actualTables(recordTable2.getActualTableNames()).dataSourceRule(dataSourceRule).build();

        /*ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule).tableRules(Arrays.asList(recordTableRule,recordTableRule2))
                .bindingTableRules(Collections.singletonList(new BindingTableRule(Arrays.asList(recordTableRule,recordTableRule2))))
                .tableShardingStrategy(new TableShardingStrategy(shardingInfo.shardingKeys, new TableShardingAlgorithm())).build();*/
        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule).tableRules(Arrays.asList(recordTableRule))
                .bindingTableRules(Collections.singletonList(new BindingTableRule(Arrays.asList(recordTableRule))))
                .tableShardingStrategy(new TableShardingStrategy(shardingInfo.shardingKeys, new TableShardingAlgorithm(Boolean.TRUE))).build();

        return shardingRule;
    }

    protected Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
       result.put(DataSourceNames.DB_WRITE, (DataSource)beanFactory.getBean(DataSourceNames.DB_WRITE, DataSource.class));
        return result;
    }


}
