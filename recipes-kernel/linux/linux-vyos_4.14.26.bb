DESCRIPTION = "Mainline Linux Kernel with VyOS patches"
SECTION = "kernel"
LICENSE = "GPLv2"

inherit kernel

S = "${WORKDIR}/linux-${PV}"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = " \
	${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.gz;name=kernel \
	file://defconfig \
	"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

SRC_URI[kernel.md5sum] = "4f1260eb0812204bf7279c9c016859fd"
SRC_URI[kernel.sha256sum] = "2f34e9ea4eefc35633a98fcb92a78dc0c2974d5b2b1eca3ef65840ead61e3af7"

LINUX_VERSION_EXTENSION = "-vyos"

INITRAMFS_IMAGE_edgerouter = "core-image-minimal-initramfs"
