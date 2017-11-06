
do_install_append() {
    ln -s syslog ${D}/etc/init.d/rsyslog
}
