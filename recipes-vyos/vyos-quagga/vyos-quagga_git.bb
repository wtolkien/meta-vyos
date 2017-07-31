SUMMARY = "BGP/OSPF/RIP/RIB routing daemons with VyOS / Vyatta extensions"
HOMEPAGE = "https://github.com/vyos/vyatta-quagga"
SECTION = "vyos/core"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

SRC_URI = "git://github.com/vyos/vyatta-quagga.git;branch=current;protocol=https \
	  "

# snapshot from Jul 13, 2017:
SRCREV = "445c195606fa062037584e612d5bc554e5c1cc30"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "readline"

FILES_${PN} += "/usr/lib"

# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit autotools-brokensep

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
       	--prefix=/usr \
       	--sbindir=/usr/sbin \
       	--libdir=/usr/lib/quagga \
       	--localstatedir=/var/run/quagga \
       	--sysconfdir=/etc/quagga \
       	--mandir=/usr/share/man \
       	--infodir=/usr/share/info/quagga \
       	--enable-exampledir=/usr/share/doc/quagga/examples/ \
       	--enable-vtysh \
       	--enable-ipv6 \
       	--enable-watchquagga \
       	--enable-opaque-lsa \
       	--enable-ospfclient=yes \
       	--enable-ospfapi=yes \
       	--enable-ospf-te \
       	--enable-multipath=64 \
       	--enable-user=quagga \
       	--enable-group=quagga \
       	--enable-vty-group=quaggavty \
       	--enable-configfile-mask=0640 \
       	--enable-logfile-mask=0640 \
       	--enable-rtadv \
       	--enable-gcc-rdynamic \
		--with-libpam \
		--disable-doc \
		"
		
#       	--enable-snmp 
	
INSANE_SKIP_${PN} = "dev-so"
	