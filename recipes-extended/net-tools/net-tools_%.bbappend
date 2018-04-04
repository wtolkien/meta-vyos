# VyOS expects arp in '/usr/sbin', OE installs it in '/sbin'
do_install_append () {
    install -d ${D}/usr/sbin
    ln -s /sbin/arp ${D}/usr/sbin
}
