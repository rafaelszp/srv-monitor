class monitor::elastic_search {

	include monitor

	if versioncmp($::puppetversion,'3.6.1') >= 0 {
	  Package {
	    allow_virtual => false,
	  }
	}

	$config_hash = {
	  'ES_USER' => 'elasticsearch',
	  'ES_GROUP' => 'elasticsearch',
	}

	$config = {
	    'node'                 => {
	      'name'               => 'elasticsearch001'
	    },
	    'index'                => {
	      'number_of_replicas' => '0',
	      'number_of_shards'   => '5'
	    },
	    'network'              => {
	      'host'               => $::ipaddress
	      #'host'               => $::guest_ip
	    }
  	}
	
	class { 'elasticsearch':
  		package_url => "http://static.sistemafieg.org.br/getin/dev/elasticsearch-1.5.2.noarch.rpm",
  		init_defaults => $config_hash,
  		config => $config,

	}

	elasticsearch::instance { 'es-01':
	  config => $config,        # Configuration hash
	  init_defaults => $config_hash, # Init defaults hash
	  datadir => [ '/var/lib/elasticsearch/elasticsearch001'],       # Data directory
	}
}