class monitor{
	
	include epel

	if versioncmp($::puppetversion,'3.6.1') >= 0 {
	  Package {
	    allow_virtual => false,
	  }
	}

	package {['java-1.8.0-openjdk']:
		ensure => installed,
		require => Class['epel'],
		allow_virtual => false
	}

}