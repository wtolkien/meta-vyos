# fix bug in original recipe that leads to warning during image do_rootfs:
# systemd startup service should be part of binary package
SYSTEMD_PACKAGES = "${PN}-bin"
SYSTEMD_SERVICE_${PN}-bin = "saslauthd.service"
SYSTEMD_AUTO_ENABLE_${PN}-bin = "disable"
