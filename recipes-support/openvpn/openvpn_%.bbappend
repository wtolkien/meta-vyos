FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Note #1: by default, Yocto openvpn uses the traditional /etc/init.d/openvpn
# script for startup, relying on systemd-sysv-generator on systems that use
# systemd. Instead, Debian provides a native systemd service file, which
# we use here.

SRC_URI += " \
    file://openvpn.service \
    file://0010-fix-pkcs11-pkg-config.patch \
    "

SYSTEMD_SERVICE_${PN} += " openvpn.service"

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
    --enable-pkcs11=yes \
    --enable-x509-alt-username=yes \
	"

DEPENDS += " pkcs11-helper"

RDEPENDS_${PN} += " pkcs11-helper"

do_install_append () {
    install -d ${D}{systemd_unitdir}/system
    install -m 644 ${WORKDIR}/openvpn.service ${D}${systemd_unitdir}/system
}

FILES_${PN} += "${systemd_unitdir}/system/openvpn.service"
