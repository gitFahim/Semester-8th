# This file contains read image, convert to numpy array, write numpy array, converting numpy array to list
# write the list(containing RGB values of every pixel, converting back to numpy array and finally converting back to image

import imageio
import numpy
from numpy import asarray
from PIL import Image
# load the image
image = Image.open('Lenna.png')
# convert image to numpy array
data = asarray(image)
#print(data)
with open('Lenna_pic_numpy_array_info.txt', 'w') as f:
    f.writelines(str(data))
list = data.tolist()

# display list
print("\nList:")
#print(list)
with open('Lenna_pic_list_info.txt', 'w') as p:
    p.writelines(str(list))

# converting list to array
arr = numpy.array(list)

#Lossy conversion from int32 to uint8. Range [3, 255]. Convert image to uint8 prior to saving to suppress this warning. >> TO avoid this warning add .astype(numpy.uint8)

imageio.imwrite('Lenna_new.png', arr.astype(numpy.uint8))