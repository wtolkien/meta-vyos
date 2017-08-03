do_install_append () {
    install -d ${D}/sbin
    ln -s /usr/sbin/ethtool ${D}/sbin
}
