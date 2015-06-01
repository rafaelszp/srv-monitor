class monitor::kibana {

	include monitor

	$elasticsearch_url = "http://${::ipaddress}:9200"

	class { 'kibana4':
	  version       => '4.0.2-linux-x64',
	  download_path => 'http://static.sistemafieg.org.br/getin/dev',
	  require => Es_Instance_Conn_Validator['es-01'],
	  port => 80,
	  elasticsearch_url => $elasticsearch_url
	}

}