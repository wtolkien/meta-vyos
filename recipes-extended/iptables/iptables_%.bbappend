# VyOS expects ethtool in '/sbin', OE installs it in '/usr/sbin',
# also install ip(6)tables-apply and iptables.xslt scripts
do_install_append () {
    install -d ${D}/sbin
    ln -s /usr/sbin/iptables ${D}/sbin

    install -d ${D}/usr/sbin
    install ${S}/iptables/iptables-apply ${D}/usr/sbin
    ln -s iptables-apply ${D}/usr/sbin/ip6tables-apply

    install -d ${D}/usr/share/iptables
    install ${S}/iptables/iptables.xslt ${D}/usr/share/iptables
}

FILES_{PN} += "/usr/share/iptables"
