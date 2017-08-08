DESCRIPTION = "Packages used by VyOS"
LICENSE = "MIT"
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

# core packages required for minimal functionality
VYOS_CORE = "\
    vyos-bash \
    vyos-cfg \
    vyos-cfg-system \
    vyos-config-migrate \
    vyos-op \
    vyos-wireless \
    vyos-quagga \
    vyos-cfg-quagga \
    vyos-openvpn \
    vyos-wirelessmodem \
    vyos-cfg-op-pppoe \
    "

VYOS_VPN = "\
    vyos-cfg-vpn \
    vyos-op-vpn \
    "

RDEPENDS_${PN} = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'vyos-core', '${VYOS_CORE}', '',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'vyos-vpn', '${VYOS_VPN}', '',d)} \
    "
