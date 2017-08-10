SUMMARY = "VyOS WAN load balance project"
HOMEPAGE = "https://github.com/vyos/vyatta-wanloadbalance"
SECTION = "vyos/net"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-wanloadbalance.git;branch=current;protocol=https \
	  "

# snapshot from Aug 10, 2017:
SRCREV = "7c0afdb7bf08c5d08447adfe98258e337433fa11"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "sed perl procps vyos-quagga vyos-cfg vyos-op vyos-bash \
	 vyos-config-migrate"

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
	install -d -m 755 ${D}/var/run/load-balance
	touch ${D}/var/run/load-balance/wlb.conf
}
