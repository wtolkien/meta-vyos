# VyOS expects ethtool in '/sbin', OE installs it in '/usr/sbin',
# also install iptables-apply script
do_install_append () {
    install -d ${D}/sbin
    ln -s /usr/sbin/iptables ${D}/sbin
    install -d ${D}/usr/sbin
    install ${S}/iptables/iptables-apply ${D}/usr/sbin
    ln -s iptables-apply ${D}/usr/sbin/ip6tables-apply
}
