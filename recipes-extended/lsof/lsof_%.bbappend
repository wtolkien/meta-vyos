# VyOS expects lsof in '/usr/bin', OE installs it in '/usr/sbin'
do_install_append () {
    install -d ${D}/usr/bin
    ln -s /usr/sbin/lsof ${D}/usr/bin
}
