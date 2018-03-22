FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://0010-fix-pkcs11-pkg-config.patch \
    "

# additional options to be passed to the configure script:
EXTRA_OECONF = "\
    --enable-pkcs11=yes \
    --enable-x509-alt-username=yes \
    --with-plugindir=${libdir}/openvpn \
	"

DEPENDS += " pkcs11-helper"

RDEPENDS_${PN} += " pkcs11-helper"
