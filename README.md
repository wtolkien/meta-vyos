# meta-vyos
A meta layer to bring VyOS router functionality to OpenEmbedded

VyOS is an open source network operating system based on GNU/Linux. It joins
multiple applications such as Quagga, ISC DHCPD, OpenVPN, StrongS/WAN and
others under a single management interface.

More information can be found at https://vyos.io

VyOS is based on Debian and currently available as a LiveCD/Install ISO for
physical and virtual 64-bit x86 machines and VMWare OVA for vSphere/vCloud Air.

So far there are no official builds for other processor architectures or embedded
platforms, however an ARM build for SolidRun's ClearFog platform does exist:

http://dev.packages.vyos.net/devices/clearfog/solidrun/

While Debian is available for many different processor architectures, it was never
explicitly designed for embedded systems. As a result, embedded platform support
lags far behind compared to other build environments such as OpenEmbedded.

This project aims to create a meta layer for OpenEmbedded that will make it
easy to run VyOS router software on a wide variety of embedded platforms.

## Current Status:

OE-VyOS boots on MT7623 SoCs and basic router functionality has been verified: Obtain IP using DHCP on one interface, run NAT and a DHCP server on another interface.

---

|Component|VyOS Package|Based on|Status|Comment|
|---|---|---|---|---|
| Operating System| vyos-kernel| linux-4.14.26| using upstream ver 4.14.26 + patches| see note 1)|
| | vyatta-bash| bash|ported| see note 2)|
| | vyatta-busybox| busybox-1.19.0| busybox 1.27.2 | required ?|
| | ipaddrcheck| | | not used ?|
| |
| Config System| vyatta-cfg| | ported| |
| | vyatta-cfg-system| | ported| |
| | vyatta-op| | ported| |
| | vyatta-config-migrate| | ported|may not be required|
| | vyatta-config-mgmt| | ported| |
| | vyatta-util| | ported| |
| | vyatta-cron| | ported| |
| |
| General Networking| vyatta-conntrack| | ported| |
| |conntrack-tools| conntrack-tools-1.4.2| using upstream ver 1.4.4 | VyOS version does not contain custom patches |
| | vyatta-conntrack-sync| | ported| |
| | vyatta-nat| | ported| |
| | vyatta-iproute| iproute2-3.12.0| using upstream ver 4.14.1 | fix-ups as per bbappend file |
| | vyos-iptables| iptables-1.4.21| using upstream ver 1.6.2 | fix-ups as per bbappend file |
| | vyatta-wanloadbalance| | ported| |
| | vyatta-lldp| | ported| |
| | lldpd| lldpd-0.6.0| using upstream ver 0.9.8 | VyOS version is old but heavily patched. TODO: check functionality, possibly switch to VyOS version |
| | vyos-keepalived| keepalived-1.2.19| using upstream ver 1.3.9 | replaces 'vyatta-keepalived', VyOS patches were obsolete (for 2.6 kernels) |
| | igmpproxy| igmpproxy-0.1 | ported | contains Ubiquiti ERL patches|
| | vyos-igmpproxy| | ported| derived from Ubiquiti ERL 1.4.1 |
| | vyatta-zone| | ported| |
| | vyatta-watchdog| | ported| |
| | ipset| ipset-6.23| using upstream ver 6.34 | Didn't find any VyOS patches other than turning it into a Debian package |
| | iputils| iputils-20121221| using upstream ver 20161105 | |
| | ppp| ppp-2.4.6| using upstream ver 2.4.7 | TODO: check postinst script from VyOS ppp repo (seems not being used any more) |
| | openssl| openssl-1.0.0| using upstream ver 1.0.2o | |
| | netplug| netplug 1.2.9| not used any more?| (network cable hotplug daemon) |
| |
| WLAN| vyatta-wireless| | ported| |
| | wpa| wpa-supplicant-1.1| TBD | TODO: WLAN support will be added later |
| |
| Routing| vyatta-quagga| quagga-0.99.14| ported| too many patches - won't use upstream|
| | vyatta-cfg-quagga| | ported| |
| | vyatta-op-quagga| | ported| |
| | vyos-frr| frr-?| TBD | may replace quagga in the future|
| | vyatta-ipv6-rtadv| | ported| |
| | radvd| radvd-1.15| using upstream ver 2.17 | using VyOS startup script |
| |
| WWAN| vyatta-wirelessmodem| | ported| TODO: remove and develop updated version |
| |
| DNS| ddclient| ddclient-3.8.2| using upstream ver 3.8.3 | added VyOS scripts, needs testing |
| |
| Tunnel| vyos-vxlan| | ported| |
| |
| VPN| vyatta-cfg-vpn| | ported| |
| | vyatta-op-vpn| | ported| |
| | vyos-strongswan| strongswan-5.3.5| using upstream ver 5.6.2 + patches| |
| | vyatta-openvpn| | ported| |
| | openvpn| Debian openvpn-2.3.4| using upstream ver 2.4.4| |
| | vyatta-ravpn| | ported| |
| | vyos-opennhrp| opennhrp-0.14.1| ported| using vyos version instead of upstream |
| | vyos-nhrp| | ported| |
| |
| SNMP| net-snmp| Debian net-snmp-5.7.2.1| using upstream ver 5.7.3 | added VyOS config and init scripts |
| |
| NTP| ntp| Debian ntp-4.2.6p5| using upstream ver 4.2.8p10 | added VyOS config and init scripts |
| |
| QoS| vyatta-cfg-qos| | ported| |
| |vyatta-op-qos| | ported| |
| |
| PPPoE| vyatta-cfg-op-pppoe| | ported| |
| | vyos-pppoe-server| | ported| |
| | rp-pppoe| Debian rp-pppoe-3.8| using upstream ver 3.12 | added missing Debian patches |
| |
| DHCP| vyatta-op-dhcp-server| | ported| |
| | vyatta-cfg-dhcp-server| | ported| |
| | vyatta-dhcp3| Debian isc-dhcp-4.3.1| using upstream ver 4.3.6 | added Debian patches and scripts |
| | vyatta-cfg-dhcp-relay| | ported| |
| |
| Monitoring| vyatta-netflow| | ported| |
| | pmacct| Debian pmacct-1.5.0| using upstream ver 1.7.0| TODO: check: VyOS repo uses 1.6.2|
| | vyatta-eventwatch| | ported| |
| | eventwatchd| eventwatchd-0.2 | ported| |
| |
| Firewall| vyatta-cfg-firewall| | ported| |
| | vyatta-op-firewall| | ported| |
| |
| Redundancy| vyatta-vrrp| | ported| |
| |
| Cluster| vyatta-cluster| | n/a| won't implement on embedded|
| | heartbeat| heartbeat-3.0.5| n/a| |
| |
| Proxy| vyatta-webproxy| | ported| |
| | squidguard| squidguard-1.4| using upstream ver 1.4 + patches| adds large amount of dependent packages|
| |
| Libraries| libnfnetlink| libnfnetlink-1.0.1| n/a| |
| | libnetfilter-cttimeout| libnetfilter-cttimeout-1.0.0| n/a| |
| | libnetfilter-cthelper| libnetfilter-cthelper-1.0.0| n/a| |
| | libnetfilter-conntrack| libnetfilter-conntrack-1.0.5| n/a| |
| | libnetfilter-queue| libnetfilter-queue1.0.2| n/a| |
| | libmnl| libmnl-1.0.3| n/a| |
| | libnl3| libnl-3.2.25| n/a| |
| | libcap| libcap-2.19| n/a| |
| |
| Build-system| vyos-build| | | double-check for anything that needs to be transferred|
| | build-iso| | | double-check for anything that needs to be transferred|
| | vyos-replace| | | double-check for anything that needs to be transferred|
| | live-boot| | n/a| |
| | vyatta-grub| | n/a| |
| | vyos-ami| | n/a| |
| | live-initramfs| | n/a| |
| | initramfs-tools| | n/a| |
| | squashfs| | n/a| |
| | vyos-world| | n/a| |
| |
| Other| vyconf| | n/a| next generation config system|
| | vyos| | n/a| VyOS 2.0|
| | emrk| | n/a| Ubiquiti EdgeMax Rescue scripts|
| | vyatta-biosdevname| | n/a| BIOS device name util (x86 only)|
| | vyatta-webgui| | n/a| Web GUI|
| | linux-firmware| | n/a| |
| | python-vyos-mgmt| | n/a| library for route management|
| | hvinfo| | n/a| hypervisor detection tools|
| | vyatta-open-vm-tools| | n/a| virtual machine tools|
| | MAC-Telnet| | n/a| Microtik tool|
| | vytest| | n/a| test suite|
| | vyatta-dummy| | n/a| template package|
| |
| Obsolete| vyos-ocserv| | n/a| empty repository|
| | iptables| | n/a| replaced by 'vyos-iptables'|
| | libzmq-constants-perl| | n/a| no longer used|
| | libzmq-libzmq2-perl| | n/a| no longer used|
| | vyatta-keepalived| | n/a| replaced by 'vyos-keepalived'|
| | vyatta-strongswan| | n/a| replaced by 'vyos-strongswan'|


### Notes:
1) different target hardware will required differnet kernels. Initial development was to build the 
    'qemux86' target, however now primary development has switched to embedded platforms using the MT7623 SoC.
    Contributions to support more target hardware is welcome.
2) VyOS uses two shells: a VyOS-modified version of bash 4.1, installed as 'vbash' and
    generic bash 4.3 installed as 'bash'. 

There is a lot more work to do and any help from interested parties is very welcome.

### Other Issues:

- Most VyOS source packages build with GNU autotools, however they don't allow
  building outside of the source directory. This prevents the usage of the
  'devtool' command that's useful for local developmend with OpenEmbedded/bitbake.
- Upstream VyOS's image management and upgrade mechanism is not well suited for 
  embedded systems with limited storage. This project aims to use 'swupdate' instead.

### Rough roadmap:

- Testing! It is now possible to boot into a VyOS prompt and configure basic router functionality.
  Firmware upgrades are possible using swupdate and the built-in Web-GUI at port 8080. Configuration
  is retained across upgrades.
  However, a lot of subsystems and services have not been tested yet. 
- Sync up with upstream VyOS. Since this project started, VyOS has seen significant improvements. Many meta-vyos 
  packages took a snapshot from upstream around 08/2017 and need to be brought up to date.
- Add hardware support. Currently only MT7623 SoCs are supported. Other attractive targets would be 
   - the EspressoBin board
   - Ubiquity hardware
   - any platform with native Gig-E ports.
- Deal with issues related to logging and frequent file system write access that
  could wear out an embedded flash filesystem
- Debug!


# Build Instructions:

Development is currently done against OpenEmbedded/Yocto, release 'sumo'

If you are not familiar with OpenEmbedded (or the 'Yocto' derivative), extensive documentation can be found here:

https://www.yoctoproject.org/documentation

* make sure you meet the required prerequisites as described here:

https://www.yoctoproject.org/docs/current/ref-manual/ref-manual.html#intro-requirements

* get the Yocto core packages, plus the 'meta-openembedded' and 'meta-swupdate' layers:
```
git clone -b sumo git://git.yoctoproject.org/poky.git yocto-sumo
cd yocto-sumo
git clone -b sumo git://git.openembedded.org/meta-openembedded
git clone https://github.com/sbabic/meta-swupdate
````
* get this project's source:
```
git clone https://github.com/wtolkien/meta-vyos.git
```
* from the ```yocto-sumo``` directory, initialize the build-environment:
```
source oe-init-build-env
```
* edit the OpenEmbedded layer configuration file ```conf/bblayers.conf``` to include
  some additional layers from 'meta-openembedded' as well as the `meta-vyos layer`.
  Your ```BBLAYERS``` variable should look as below, however you may have to adjust
  your path
```
BBLAYERS ?= " \
  [your existing path]/yocto-sumo/meta \
  [your existing path]/yocto-sumo/meta-poky \
  [your existing path]/yocto-sumo/meta-yocto-bsp \
  \
  ${TOPDIR}/../meta-vyos \
  ${TOPDIR}/../meta-openembedded/meta-oe \
  ${TOPDIR}/../meta-openembedded/meta-networking \
  ${TOPDIR}/../meta-openembedded/meta-python \
  ${TOPDIR}/../meta-openembedded/meta-perl \
  ${TOPDIR}/../meta-openembedded/meta-filesystems \
  \
  ${TOPDIR}/../meta-swupdate \
  "
```
* add the following line to the OpenEmbedded config file ```conf/local.conf``` to
  to build a 'vyos' distro. This line can be added anywhere in the config file:
```
DISTRO ?= "vyos"
```
* if you are building an embedded target, add the corresponding machine layer (for example 
  `meta-mediatek`) and set the `MACHINE` environment variable:
```
export MACHINE=mt7623-bpi-r2
```
* you are now ready to start the build process. From the ```yocto-sumo/build``` directory,
  enter:
```
bitbake vyos-image
```
or 
```
bitbake vyos-image-swu
```
to build a swupdate upgrade file.
* When done (likely after an hour or more), an image will be in the
  ```yocto-sumo/build/tmp-glibc/deploy/image/$MACHINE``` directory. In case of `qemux86` can be started by entering
```
runqemu
```

## Acknowledgements:

Credit goes to the VyOS team for contributing and maintaining such a great router platform! Kudos
to Kim Hagen for having ported VyOS to the ClearFog platform and thereby inspiring this project!
