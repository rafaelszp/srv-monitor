class monitor::logs {

	include monitor
	

	if versioncmp($::puppetversion,'3.6.1') >= 0 {
	  Package {
	    allow_virtual => false,
	  }
	}

	class { 'logstash': 
		package_url => "http://static.sistemafieg.org.br/getin/dev/logstash-1.5.0-1.noarch.rpm",
		#ensure => 'absent'
	}

	logstash::configfile { 'configname':
  		content => template('monitor/logstash.conf'),
	}

	file{'/etc/pki/tls/openssl.cnf':
		content => template('monitor/openssl.cnf'),
		mode => 0644,
		alias => 'config-openssl'
	}

	exec {'generate-cert':
		unless => '/usr/bin/ls /etc/pki/tls/private/ | /usr/bin/grep logstash-forwarder.key',
		subscribe => File['config-openssl'],
		command => '/usr/bin/sh /vagrant/modules/monitor/scripts/generate-certificate.sh'
	}
	
}