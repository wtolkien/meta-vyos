# additional options to be passed to the configure script:
EXTRA_OECONF = "\
    --enable-pkcs11=yes \
    --enable-x509-alt-username=yes \
    --with-plugindir=${libdir}/openvpn \
	"

DEPENDS += " pkcs11-helper"

RDEPNDS_${PN} += " pkcs11-helper"
