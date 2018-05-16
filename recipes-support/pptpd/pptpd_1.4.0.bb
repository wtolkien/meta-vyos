DESCRIPTION = "Poptop is the PPTP server solution for Linux Using Poptop, \
Linux servers can now function seamlessly in a PPTP VPN environment. This \
enables administrators to leverage the considerable benefits of both \
Microsoft and Linux operating systems The current release version supports \
Windows 95/98/Me/NT/2000/XP PPTP clients and Linux PPTP clients"
HOMEPAGE = "http://www.poptop.org/"
SECTION = "network"
DEPENDS = "ppp xl2tpd"
RDEPENDS_${PN} = "ppp"
RDEPENDS_${PN}-logwtmp-plugin = "${PN}"
PR = "r5"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "${SOURCEFORGE_MIRROR}/poptop/pptpd-${PV}.tar.gz \
           file://fix-plugins-install.patch \
           file://include-dummy-pppd-h.patch \
           file://pptpd.init \
           file://pptpd.service \
           file://pptpd-1.4.0/plugins/pppd.h"

S = "${WORKDIR}/pptpd-${PV}"

FILES_${PN} += "/usr/lib"

inherit autotools-brokensep systemd

do_install_append() {
        # Install init script
        install -m 0755 -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/pptpd.init ${D}${sysconfdir}/init.d/pptpd
        # Install
        install -d ${D}${sbindir} ${D}/${sysconfdir} ${D}/${sysconfdir}/ppp
        install -m 0644 samples/options.pptpd ${D}/${sysconfdir}/ppp/
        install -m 0644 samples/pptpd.conf ${D}/${sysconfdir}/

        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/pptpd.service ${D}${systemd_unitdir}/system

        # broken (doesn't cross-compile, maybe other issues...)
	sed -ri "s,^[:space:]*logwtmp[:space:]*,# logwtmp," ${D}/${sysconfdir}/pptpd.conf
        rm -f ${D}${libdir}/pptpd/pptpd-logwtmp.so
	# Use mppe option in OE style
	sed -ri "s|^[[:space:]]*require-mppe-128[[:space:]]*$|mppe required,no40,no56|" ${D}/${sysconfdir}/ppp/options.pptpd
	echo "# don't expose open port by default" >> ${D}/${sysconfdir}/pptpd.conf
	echo "listen  127.0.0.1" >> ${D}/${sysconfdir}/pptpd.conf
}

PACKAGES = "${PN}-dbg ${PN}-bcrelay ${PN} ${PN}-doc"

FILES_${PN}-bcrelay = "${sbindir}/bcrelay"

CONFFILES_${PN} = "${sysconfdir}/pptpd.conf \
                   ${sysconfdir}/ppp/options.pptpd"

# no autostart at startup - VyOS will control this
SYSTEMD_SERVICE_${PN} = "pptpd.service"
SYSTEMD_AUTO_ENABLE = "disable"

SRC_URI[md5sum] = "36f9f45c6ffa92bc3b6e24ae2d053505"
SRC_URI[sha256sum] = "8fcd8b8a42de2af59e9fe8cbaa9f894045c977f4d038bbd6346a8522bb7f06c0"
