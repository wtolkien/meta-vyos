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
    "

RDEPENDS_${PN} = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'vyos', '${VYOS_CORE}', '',d)} \
    "
