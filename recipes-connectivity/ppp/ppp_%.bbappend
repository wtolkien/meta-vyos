# remove Yocto's default startup file - we don't need it
do_install_append () {
    rm -f ${D}${sysconfdir}/init.d/ppp
}
