FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://sudo.service"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/sudo.service ${D}${systemd_unitdir}/system
}

inherit systemd

SYSTEMD_SERVICE_${PN} = "sudo.service"
