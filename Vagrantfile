#SRV-logmonitor
# => Configuração para servidor logmonitor
# => Requisitos:
# => vagrant plugin install vagrant-librarian-puppet
#

VAGRANTFILE_API_VERSION	= "2"
GUEST_IP				= "192.168.2.10"
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

	config.librarian_puppet.puppetfile_dir	= "librarian"
	config.vm.box = "puppetlabs/centos-7.0-64-puppet"
	config.vm.define :logmonitor do |logmonitor| 

		logmonitor.vm.hostname = "logmonitor"

		logmonitor.vm.provider "virtualbox" do |logmonitor_vb| 
			logmonitor_vb.memory = "1024"
		end

		logmonitor.vm.provision "puppet" do |puppet|
			puppet.module_path		= ["modules","librarian/modules"]
			puppet.manifest_file	= "logmonitor.pp"
			puppet.facter = {
		    	"guest_ip" => GUEST_IP,
		    }
			#puppet.options = '--verbose --debug'
		end

		logmonitor.vm.network :private_network, ip: GUEST_IP
		logmonitor.vm.network "forwarded_port", guest:9200, host: 9200, auto_correct: true
		logmonitor.vm.network "forwarded_port", guest:5000, host: 5000, auto_correct: true
		logmonitor.vm.network "forwarded_port", guest:6666, host: 6666, protocol: 'udp', auto_correct: true
		logmonitor.vm.network "forwarded_port", guest:80, host: 8380, auto_correct: true

	end

end