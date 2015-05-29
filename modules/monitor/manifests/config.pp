class monitor::config {

	include monitor

	if versioncmp($::puppetversion,'3.6.1') >= 0 {
	  Package {
	    allow_virtual => false,
	  }
	}

	package {['wget','vim-enhanced','net-tools','mlocate','ntsysv','glances','telnet','ntpdate']:
		ensure => installed,
		require => Class['epel'],
		allow_virtual => false
	}

	exec {'updatedb':
		require => Package['mlocate'],
		command => '/usr/bin/sudo updatedb',
	}

	exec {'disable-firewall':
		command => '/usr/bin/sudo /bin/systemctl disable firewalld && /usr/bin/sudo /bin/systemctl stop firewalld',
	}

	exec {'update-datetime':
		command => '/usr/bin/sudo /usr/sbin/ntpdate 10.21.152.152',
		require => Package[ntpdate],
	}

}