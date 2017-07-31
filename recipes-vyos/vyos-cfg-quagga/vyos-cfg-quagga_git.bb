SUMMARY = "VyOS configuration templates and scripts for Quagga"
HOMEPAGE = "https://github.com/vyos/vyatta-cfg-quagga"
SECTION = "vyos/core"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyatta-cfg-quagga.git;branch=current;protocol=https \
	  "

# snapshot from Jul 28, 2017:
SRCREV = "371a82ff2a351f33188e3dec7fabb6d3dbb5ffb0"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-quagga"

FILES_${PN} += "/opt"

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

do_install_append () {
	install -d ${D}/opt/vyatta/sbin/policy

	# Create a directory for autosaved quagga configs
	install -d -m 770 ${D}/opt/vyatta/etc/quagga
}

pkg_postinst_${PN} () {
	if [ x"$D" = "x" ]; then

		# get owner/group setting changes out of the way...
		chgrp vyattacfg /opt/vyatta/etc/quagga

		if [ -f /usr/bin/vtysh ] && [ ! -f /opt/vyatta/sbin/policy/vtysh ]; then
   			ln -s /usr/bin/vtysh /opt/vyatta/sbin/policy/vtysh
		fi
	else
   		exit 1
	fi
}
