SUMMARY = "The VyOS Router Image"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

IMAGE_INSTALL = " \
    packagegroup-core-boot \
    packagegroup-vyos \
    kernel-modules \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "

IMAGE_LINGUAS = " "

inherit core-image

IMAGE_OVERHEAD_FACTOR = "1.2"
IMAGE_FSTYPES += "squashfs"

# create home directory for vyos user
python do_rootfs_append () {
    bb.build.exec_func('add_vyos_home_dir', d)
}

add_vyos_home_dir() {
    install -d ${IMAGE_ROOTFS}/home
    install -o vyos -g users -d ${IMAGE_ROOTFS}/home/vyos
}
