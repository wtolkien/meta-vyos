SUMMARY = "VyOS PPPoE Server conf & op templates"
HOMEPAGE = "https://github.com/vyos/vyatta-pppoe-server"
SECTION = "vyos/pppoe"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/vyos/vyos-pppoe-server.git;branch=current;protocol=https \
	  "

# snapshot from Aug 14, 2017:
SRCREV = "e4f7c0581a3cf5b745259ef5e87358a9a2ea6d41"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "vyos-bash"
RDEPENDS_${PN} = "perl vyos-cfg vyos-op vyos-bash ppp rp-pppoe libfreeradius-client2"

FILES_${PN} += "/opt /usr/share"


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
	install -d ${D}/etc/ppp/secrets
	touch ${D}/etc/ppp/secrets/chap-pppoe-server
	chmod 600 ${D}/etc/ppp/secrets/chap-pppoe-server

	install -d ${D}/etc/radiusclient-ng
	touch ${D}/etc/radiusclient-ng/radiusclient-pppoe.conf
	touch ${D}/etc/radiusclient-ng/servers-pppoe

	install -d ${D}/etc/ppp
	touch ${D}/etc/ppp/pppoe-server-options

	install -d ${D}/opt/vyatta/etc/pppoe-server/sessions

	for s in up down; do
		install -d ${D}/etc/ppp/ip-$s.d
		cp -f ${D}/opt/vyatta/etc/pppoe-server/ppp-ip-$s \
			${D}/etc/ppp/ip-$s.d/pppoe-server-ip-$s
	done
}

# perform some post-installation actions, but only on target device, not at
# build time (a lot of this probably should be moved to build time, for now
# they were just copied here from Debian's postinst script)
pkg_postinst_${PN} () {
	if [ x"$D" = "x" ]; then

		# TODO: check the following postinst commands
# remove init of daemons that we start/stop
#for init in pppoe-server; do
#  update-rc.d -f ${init} remove >/dev/null
#done

	else
		exit 1
	fi
}
