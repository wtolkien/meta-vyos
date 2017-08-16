SUMMARY = "VyOS Configuration and operational mode templates for the PPPOE feature"
HOMEPAGE = "https://github.com/vyos/vyatta-cfg-op-pppoe"
SECTION = "vyos/pppoe"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-cfg-op-pppoe.git;branch=current;protocol=https \
	  "

# snapshot from Aug 1, 2017:
SRCREV = "dbc344c47eb3c35b89087e551e3b0901a1acadf7"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"

FILES_${PN} += "/opt"

# NOTE: this software seems not capable of being built in a separate build directory
# from the source, therefore using 'autotools-brokensep' instead of 'autotools'
inherit autotools-brokensep	update-rc.d

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
    --prefix=/opt/vyatta \
    --exec_prefix=/opt/vyatta \
	--sbindir=/opt/vyatta/sbin \
	--bindir=/opt/vyatta/bin \
	--datadir=/opt/vyatta/share \
	--sysconfdir=/opt/vyatta/etc \
	"

do_install_append () {
	install -d ${D}/etc/init.d
	install debian/vyatta-pppoe.init ${D}/etc/init.d/vyatta-pppoe
}

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "vyatta-pppoe"
INITSCRIPT_PARAMS_${PN} = "start 10 2 3 4 5 ."
