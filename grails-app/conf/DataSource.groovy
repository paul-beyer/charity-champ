dataSource {
    pooled = true
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
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
//			dbCreate = ""
//			url = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com:3306/charity_champ_db"
//			username = "charityChamp"
//			password = "FoodBank12"
//			driverClassName = "com.mysql.jdbc.Driver"
//			dialect = org.hibernate.dialect.MySQL5InnoDBDialect
        }
    }
    test {
        dataSource {
            dbCreate = ""
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
        }
    }
	
    production {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//			driverClassName = "org.h2.Driver"
//			username = "sa"
//			password = ""
      //      jndiName = "java:/jdbc/charityChamp"
			
        }
    }
}
