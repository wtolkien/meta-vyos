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

|Component|VyOS Package|Based on|Status|Comment|
|---|---|---|---|---|
| Operating System| vyos-kernel| linux-4.4.48| using upstream ver 4.4 + patches| see note 1)|
| | vyatta-bash| bash|ported| see note 2)|
| | vyatta-busybox| busybox-1.19.0| | not used ?|
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
| |conntrack-tools| conntrack-tools-1.4.2| | |
| | vyatta-conntrack-sync| | ported| |
| | vyatta-nat| | ported| |
| | vyatta-iproute| iproute2-3.12.0| | |
| | vyos-iptables| iptables-1.4.21| | |
| | vyatta-wanloadbalance| | ported| |
| | vyatta-lldp| | ported| |
| | lldpd| lldpd-0.6.0| | |
| | vyos-keepalived| keepalived-1.2.19| | replaces 'vyatta-keepalived'|
| | igmpproxy| igmpproxy-1.0.1| | contains Ubiquiti ERL patches|
| | vyos-igmpproxy| | ported| derived from Ubiquiti ERL 1.4.1 |
| | vyatta-zone| | ported| |
| | vyatta-watchdog| | ported| |
| | ipset| ipset-6.19.1| | |
| | iputils| iputils-20101006| | |
| | ppp| ppp-2.4.4| | |
| | openssl| openssl-0.9.8zg| | |
| | netplug| netplug 1.2.9| | |
| |
| WLAN| vyatta-wireless| | ported| |
| | wpa| wpa-supplicant-1.1| | |
| |
| Routing| vyatta-quagga| quagga-0.99.14| ported| too many patches - won't use upstream|
| | vyatta-cfg-quagga| | ported| |
| | vyatta-op-quagga| | ported| |
| | vyos-frr| frr-?| | may replace quagga in the future|
| | vyatta-ipv6-rtadv| | ported| |
| | radvd| radvd-1.15| | |
| |
| WWAN| vyatta-wirelessmodem| | ported| |
| |
| DNS| ddclient| ddclient-3.8.2| | |
| |
| Tunnel| vyos-vxlan| | ported| |
| |
| VPN| vyatta-cfg-vpn| | ported| |
| | vyatta-op-vpn| | ported| |
| | vyos-strongswan| strongswan-5.3.5| using upstream ver 5.5.3 + patches| |
| | vyatta-openvpn| | ported| |
| | openvpn| openvpn-2.2.3| | |
| | vyatta-ravpn| | ported| |
| | vyos-opennhrp| opennhrp-0.14.1| | using vyos version instead of upstream |
| | vyos-nhrp| | ported| |
| |
| SNMP| net-snmp| net-snmp-5.7.2.1| | |
| |
| NTP| ntp| ntp-4.2.4| | |
| |
| QoS| vyatta-cfg-qos| | | |
| |vyatta-op-qos| | | |
| |
| PPPoE| vyatta-cfg-op-pppoe| | ported| |
| | vyos-pppoe-server| | | |
| | rp-pppoe| rp-pppoe-1.3.11| | |
| |
| DHCP| vyatta-op-dhcp-dserver| | | |
| | vyatta-cfg-dhcp-server| | | |
| | vyatta-dhcp3| isc-dhcp-4.1.1| | |
| | vyatta-cfg-dhcp-relay| | | |
| |
| Monitoring| vyatta-netflow| | | |
| | pmacct| pmacct-1.6.2| | |
| | vyatta-eventwatch| | |
| | eventwatchd| eventwatchd-0.2 | | |
| |
| Firewall| vyatta-cfg-firewall| | | |
| | vyatta-op-firewall| | | |
| | 
| Redundancy| vyatta-vrrp| | | |
| | 
| Cluster| vyatta-cluster| | | |
| | heartbeat| heartbeat-3.0.5|  | |
| | 
| Proxy| vyatta-webproxy| | | |
| | squidguard| squidguard-1.4| | |
| | 
| Libraries| libnfnetlink| libnfnetlink-1.0.1| | |
| | libnetfilter-cttimeout| libnetfilter-cttimeout-1.0.0| | |
| | libnetfilter-cthelper| libnetfilter-cthelper-1.0.0| | |
| | libnetfilter-conntrack| libnetfilter-conntrack-1.0.5| | |
| | libnetfilter-queue| libnetfilter-queue1.0.2| | |
| | libmnl| libmnl-1.0.3| | |
| | libnl3| libnl-3.2.25| | |
| | libcap| libcap-2.19| |
| |
| Build-system| vyos-build| | | |
| | build-iso| | | |
| | vyos-replace| | | |
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
1) this project currently builds for the 'qemux86' target. Other targets will require 
    a different kernel package.
2) VyOS uses two shells: a VyOS-modified version of bash 4.1, installed as 'vbash' and 
    generic bash 4.3 installed as 'bash'. This project currently installs 'vbash' and symlinks
    'bash' to it.


There is a lot more work to do and any help from interested parties is very welcome.

### Other Issues:

- Most VyOS source packages build with GNU autotools, however they don't allow 
  building outside of the source directory. This prevents the usage of the 
  'devtool' command that's useful for local developmend with OpenEmbedded/bitbake.
- Image management is not working, and it may never work in the same way usually
  does with Debian VyOS. Debian VyOS uses Debian's LiveCD architecture with
  initramfs/squashfs to handle multiple images. It is not clear yet how this
  translates into the embedded realm, or even if it makes sense at all.
- Even for packages that have already been ported, there are likely going to be 
  a fair number of other bugs that stem from differences between core Debian 
  vs. OpenEmbedded packages.


### Rough roadmap:

- Port over more VyOS features by creating recipes for other VyOS packages
- Resolve issues related to image management / firmware upgrade. Either find a
  way to use the existing 'VyOS way' with OpenEmbedded, or design something new
  that possibly makes more sense in an embedded environment
- Deal with issues related to logging and frequent file system write access that
  could wear out an embedded flash filesystem
- Debug!


# Build Instructions:

Development is currently done against OpenEmbedded (release 'pyro'), however other
OpenEmbedded-based distributions will likely work as well. If you are not familiar
with OpenEmbedded (or the 'Yocto' derivative), extensive documentation can be found 
here:

https://www.yoctoproject.org/documentation

* make sure you install the required packages as described here:

https://www.openembedded.org/wiki/Getting_started#Systems_based_upon_OE-Core

* get the OpenEmbedded core packages, the 'meta-openembedded' layer and the 'bitbake'
sources:
```
git clone git://git.openembedded.org/openembedded-core oe-core
cd oe-core
git clone git://git.openembedded.org/meta-openembedded
git clone git://git.openembedded.org/bitbake bitbake
git checkout pyro
cd meta-openembedded
git checkout pyro
cd ..
cd bitbake
git checkout 1.34
cd ..
````
* get this project's source:
```
git clone https://github.com/wtolkien/meta-vyos.git
```
* from the ```oe-core``` directory, initialize the build-environment:
```
source oe-init-build-env
```
* edit the OpenEmbedded layer configuration file ```conf/bblayers.conf``` to include
  some additional layers from 'meta-openembedded' as well as the `meta-vyos layer`.
  Your ```BBLAYERS``` variable should look as below, however you may have to adjust 
  your path
```
BBLAYERS ?= " \
  [your existing path]/oe-core/meta \
  ${TOPDIR}/../meta-openembedded/meta-oe \
  ${TOPDIR}/../meta-openembedded/meta-networking \
  ${TOPDIR}/../meta-openembedded/meta-python \
  ${TOPDIR}/../meta-openembedded/meta-perl \
  ${TOPDIR}/../meta-openembedded/meta-filesystems \
  ${TOPDIR}/../meta-vyos \
  "
```
* add the following line to the OpenEmbedded config file ```conf/local.conf``` to 
  to build a 'vyos' distro. This line can be added anywhere in the config file:
```
DISTRO ?= "vyos"
```
* you are now ready to start the build process. From the ```oe-core/build``` directory,
  enter:
```
bitbake vyos-image
```
* When done (likely after 45 minutes or more), an image will be in the
  ```oe-core/build/tmp-glibc/deploy/image/qemux86``` directory. It can be started by entering
```
runqemu
```

## Acknowledgements:

Credit goes to the VyOS team for contributing and maintaining such a great router platform! Kudos 
to Kim Hagen for having ported VyOS to the ClearFog platform and thereby inspiring this project!
