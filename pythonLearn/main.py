import pandas as pd
from sklearn.datasets import load_digits
import matplotlib.pyplot as plt
digits = load_digits()
print(dir(digits))
plt.gray()
for i in range(4):
    plt.matshow(digits.images[i])
df = pd.DataFrame(digits.data)
print(df.head())
