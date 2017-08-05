FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://conntrackd.conf \
    "

do_install_append () {
    install -m 0644 ${WORKDIR}/conntrackd.conf ${D}/${sysconfdir}/conntrackd
    rm ${D}/${sysconfdir}/conntrackd/conntrackd.conf.sample
}
