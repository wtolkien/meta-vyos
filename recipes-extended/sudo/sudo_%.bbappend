FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://initscript"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${WORKDIR}/initscript ${D}${sysconfdir}/init.d/sudo
}

inherit update-rc.d

INITSCRIPT_NAME = "sudo"
INITSCRIPT_PARAMS = "defaults 2"
