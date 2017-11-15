RDEPENDS_${PN} += " ${PN}-omshell ${PN}-relay ${PN}-server ${PN}-server-config"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://0010-dhclient-script-exitstatus.patch \
    file://0020-dhclient-exit-hook.patch \
    file://0030-dhclient-dividebyzero.patch \
    file://0040-dhclient-64bit-time.patch \
    file://0050-no-loopback-checksum.patch \
    file://dhclient-enter-hooks.debug \
    file://dhclient-exit-hooks.debug \
    file://dhclient-exit-hooks.rfc3442 \
    file://dhcp-relay.init \
    file://dhcp-lease-list \
    file://dhcpd.conf \
    "

# disable /etc/init.d/pppoe-server startup script (VyOS takes care of this)
INITSCRIPT_PARAMS_dhcp-server = "remove"


do_install_append_${PN}() {
    install -d ${D}${sysconfdir}/dhcp/dhclient-enter-hooks.d
    install ${WORKDIR}/dhclient-enter-hooks.debug \
        ${D}${sysconfdir}/dhcp/dhclient-enter-hooks.d/debug
    install -d ${D}${sysconfdir}/dhcp/dhclient-exit-hooks.d
    install ${WORKDIR}/dhclient-exit-hooks.debug \
        ${D}${sysconfdir}/dhcp/dhclient-exit-hooks.d/debug
    install ${WORKDIR}/dhclient-exit-hooks.rfc3442 \
        ${D}${sysconfdir}/dhcp/dhclient-exit-hooks.d/rfc3442-classless-routes

    install -d ${D}${localstatedir}/lib/dhcp
    touch ${D}${localstatedir}/lib/dhcp/dhclient.leases
}
FILES_${PN} += " ${sysconfdir}/dhcp ${localstatedir}/lib/dhcp"

do_install_append_${PN}-relay() {
    install -d ${D}${sysconfdir}/init.d
    install ${WORKDIR}/dhcp-relay.init ${D}${sysconfdir}/init.d/isc-dhcp-relay
}
FILES_${PN}-relay += " ${sysconfdir}/init.d"

do_install_append_${PN}-server() {
    install -d ${D}${sbindir}
    install ${WORKDIR}/dhcp-lease-list ${D}${sbindir}
    install -d ${D}${sysconfdir}/dhcp
    install ${WORKDIR}/dhcpd.conf ${D}${sysconfdir}/dhcp
    install -d ${D}${sysconfdir}/init.d
    ln -s dhcp-server ${D}${sysconfdir}/init.d/isc-dhcp-server
}
FILES_${PN}-server += " ${sysconfdir}/dhcp ${sysconfdir}/init.d"
