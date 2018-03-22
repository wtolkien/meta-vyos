RDEPENDS_${PN} += " vyos-bash"


# enable libnfnetlink to pull in OSF (Operating system
# fingerprinting) support
PACKAGECONFIG += "libnfnetlink"

# create symlinks to provide fix issues caused by different
# paths in VyOS/Debian vs. OpenEmbedded.
# also install ip(6)tables-apply and iptables.xslt scripts
do_install_append () {
    install -d ${D}/sbin
    for i in iptables iptables-restore iptables-save \
             ip6tables ip6tables-restore ip6tables-save; do \
        ln -s /usr/sbin/xtables-multi ${D}/sbin/${i}; \
    done

    ln -s /usr/sbin/nfnl_osf ${D}/sbin

    install -d ${D}/usr/sbin
    install ${S}/iptables/iptables-apply ${D}/usr/sbin
    ln -s iptables-apply ${D}/usr/sbin/ip6tables-apply

    install -d ${D}/usr/share/iptables
    install ${S}/iptables/iptables.xslt ${D}/usr/share/iptables
}

FILES_{PN} += "/usr/share/iptables"
