# VyOS expects ethtool in '/sbin', OE installs it in '/usr/sbin'
do_install_append () {
    install -d ${D}/sbin
    ln -s /usr/sbin/iptables ${D}/sbin
}
