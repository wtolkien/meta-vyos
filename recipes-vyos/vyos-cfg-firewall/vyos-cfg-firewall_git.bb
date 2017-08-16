SUMMARY = "VyOS Configuration templates and scripts for the firewall subsystem"
HOMEPAGE = "https://github.com/vyos/vyatta-cfg-firewall"
SECTION = "vyos/firewall"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-cfg-firewall.git;branch=current;protocol=https \
	  "

# snapshot from Aug 10, 2017:
SRCREV = "c903db0a63b627e1cdfa91ded522c73abb3b0516"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "sed perl procps vyos-cfg vyos-op vyos-bash vyos-cfg vyos-cfg-system \
	 vyos-util ntp inetutils sudo net-snmp iptables vyos-config-migrate libswitch-perl"

FILES_${PN} += "/opt /run"


# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit autotools-brokensep

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
    --prefix=/opt/vyatta \
    --exec_prefix=/opt/vyatta \
	--sbindir=/opt/vyatta/sbin \
	--bindir=/opt/vyatta/bin \
	--datadir=/opt/vyatta/share \
	--sysconfdir=/opt/vyatta/etc \
	"

do_install_append() {
	install -d ${D}/opt/vyatta/bin/sudo-users
	ln -sf /opt/vyatta/sbin/vyatta-ipset.pl ${D}/opt/vyatta/bin/sudo-users
}
