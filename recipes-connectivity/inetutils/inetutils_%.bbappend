# remove some apps that are provided by busybox and that would otherwise
# pull in the xinetd package...

PACKAGES_${PN}_remove = "${PN}-rshd ${PN}-ftdp ${PN}-tftpd ${PN}-telnetd"
RDEPENDS_${PN}_remove = "xinetd"
