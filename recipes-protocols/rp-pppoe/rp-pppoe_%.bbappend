RDEPENDS_${PN} += " ${PN}-server ${PN}-relay ${PN}-sniff "

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://0010-force-hw-address.patch \
    file://dsl-provider \
    "

# disable /etc/init.d/pppoe-server startup script (VyOS takes care of this)
INITSCRIPT_PARAMS_${PN}-server = "remove"


do_install_append() {
    install -d ${D}${sysconfdir}/ppp/peers
    install ${WORKDIR}/dsl-provider ${D}${sysconfdir}/ppp/peers
}

FILES_${PN} += " ${sysconfdir}/ppp/peers"
