SUMMARY = "The VyOS Router Image"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"

IMAGE_FEATURES_append = " ssh-server-openssh"

IMAGE_INSTALL_append = " packagegroup-vyos"

# create home directory for vyos user
python do_rootfs_append () {
    bb.build.exec_func('add_vyos_home_dir', d)
}

add_vyos_home_dir() {
    install -d ${IMAGE_ROOTFS}/home
    install -o vyos -g users -d ${IMAGE_ROOTFS}/home/vyos
}
