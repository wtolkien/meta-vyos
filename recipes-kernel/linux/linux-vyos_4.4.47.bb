DESCRIPTION = "Mainline Linux Kernel with VyOS patches"
SECTION = "kernel"
LICENSE = "GPLv2"

inherit kernel

PR = "r1"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.gz;name=kernel \
    file://linux-4-4-47-vyos-additions.patch \
    file://defconfig \
    "


SRC_URI[kernel.md5sum] = "96b2bebe957c8c23caae6af92f9df365"
SRC_URI[kernel.sha256sum] = "c392fb360290e70a88b9bd81d3ab9d42b915ee1cb24fabcf44e3bebd26724efb"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

S = "${WORKDIR}/linux-${PV}"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

COMPATIBLE_MACHINE = "qemuarm|qemuarm64|qemux86|qemuppc|qemumips|qemumips64|qemux86-64"

LINUX_VERSION_EXTENSION = "-vyos"
