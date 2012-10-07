dataSource {
//    pooled = true
//    driverClassName = "org.h2.Driver"
//    username = "sa"
//    password = ""
	
	driverClassName = "com.mysql.jdbc.Driver"
	dialect = org.hibernate.dialect.MySQL5InnoDBDialect
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			dbCreate = "create-drop"
			url = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com:3306/charity_champ_db"
			username = "charityChamp"
			password = "FoodBank12"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
	
    production {
       dataSource {
           dbCreate = "update"
           url = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com:3306/charity_champ_db"
           username = "charityChamp"
           password = "FoodBank12"
       }
    }
}
