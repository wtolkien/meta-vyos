# add 'traceroute6' link
do_install_append() {
    ln -s ${BPN} ${D}${bindir}/${BPN}6
}
