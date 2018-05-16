RDEPENDS_${PN} += " \
    ${PN}-omshell \
    ${PN}-relay \
    ${PN}-server \
    ${PN}-server-config \
    ${PN}-client \
    ${PN}-libs \
    "
RDEPENDS_${PN}-server += " \
    perl \
    "
RDEPENDS_${PN}-client += " \
    bash \
    "

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://0010-dhclient-script-exitstatus.patch \
    file://0020-dhclient-exit-hook.patch \
    file://0030-dhclient-dividebyzero.patch \
    file://0040-dhclient-64bit-time.patch \
    file://0050-no-loopback-checksum.patch \
    file://0060-disable-isc-warning.patch \
    file://0070-add-status-counter-feature.patch \
    file://0080-write-shared-net-to-leasefile.patch \
    file://dhclient-enter-hooks.debug \
    file://dhclient-exit-hooks.debug \
    file://dhclient-exit-hooks.rfc3442 \
    file://dhcp-relay.init \
    file://dhcp-lease-list \
    file://dhcpd.conf \
    file://dhclient-script \
    "

SYSTEMD_AUTO_ENABLE_${PN}-server = "disable"

do_install_append() {
    install -d ${D}${sysconfdir}/dhcp/dhclient-enter-hooks.d
    install ${WORKDIR}/dhclient-enter-hooks.debug \
        ${D}${sysconfdir}/dhcp/dhclient-enter-hooks.d/debug
    install -d ${D}${sysconfdir}/dhcp/dhclient-exit-hooks.d
    install ${WORKDIR}/dhclient-exit-hooks.debug \
        ${D}${sysconfdir}/dhcp/dhclient-exit-hooks.d/debug
    install ${WORKDIR}/dhclient-exit-hooks.rfc3442 \
        ${D}${sysconfdir}/dhcp/dhclient-exit-hooks.d/rfc3442-classless-routes

    install -d ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/dhclient-script \
        ${D}${base_sbindir}

    install -d ${D}${localstatedir}/lib/dhcp
    touch ${D}${localstatedir}/lib/dhcp/dhclient.leases

    install -d ${D}${sbindir}
    install ${WORKDIR}/dhcp-lease-list ${D}${sbindir}

    install -d ${D}${sysconfdir}/dhcp
    install ${WORKDIR}/dhcpd.conf ${D}${sysconfdir}/dhcp
}

FILES_${PN}-client += " \
    ${sysconfdir}/dhcp/dhclient-enter-hooks.d/debug \
    ${sysconfdir}/dhcp/dhclient-exit-hooks.d/debug \
    ${sysconfdir}/dhcp/dhclient-exit-hooks.d/rfc3442-classless-routes \
    ${localstatedir}/lib/dhcp/dhclient.leases \
    ${base_sbindir}/dhclient-script \
    "

FILES_${PN}-server += " \
    ${sysconfdir}/dhcp/dhcpd.conf \
    ${sbindir}/dhcp-lease-list \
    "

# use internal libisc libraries which are based on bind 9.9.11 - there
# is a bug in bind 9.10.x (normally supplied by Yocto) that prevents
# dhcpd/dhclient from shutting down cleanly on sigterm and from running
# in the background
#
# [https://bugzilla.yoctoproject.org/show_bug.cgi?id=12744]
#
# This workaround isn't perfect: the bind 9.9.11 libraries will be build
# in ${S}/bind rather than the build directory. But it seems to work for now.
EXTRA_OECONF += " \
    --without-libbind \
	"

SRC_URI_remove = " \
    file://0008-tweak-to-support-external-bind.patch \
    "

# prevent parallel make because we need those libraries to be build first...
PARALLEL_MAKE = "-j 1"

# this means we no longer depend on the external bind
DEPENDS_remove = "bind"
