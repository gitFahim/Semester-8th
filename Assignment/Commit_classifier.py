#!/usr/bin/env python
# coding: utf-8

# In[12]:


# All purpose 
import pandas as pd
import numpy as np
import re
from numpy import mean
from numpy import std
# Visualization
import seaborn as sns
import matplotlib.pyplot as plt

# Sklearn ML
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.ensemble import RandomForestClassifier, AdaBoostClassifier
from sklearn import metrics
from sklearn import preprocessing
from sklearn.model_selection import cross_val_score
from sklearn.metrics import cohen_kappa_score
from sklearn.metrics import confusion_matrix
from sklearn.metrics import classification_report
from sklearn.metrics import precision_score
from sklearn.metrics import precision_recall_fscore_support as score
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.model_selection import RepeatedStratifiedKFold
from sklearn import tree
from sklearn.metrics import cohen_kappa_score, make_scorer
#from sklearn.grid_search import GridSearchCV
from sklearn.svm import LinearSVC
import sys
sys._enablelegacywindowsfsencoding()
pd.set_option('display.max_rows', None)


# In[2]:


#fields = ['comment', 'label']
data = pd.read_csv("E:\Semester 8\Software Maintenance\Assignment\dataset.csv", skipinitialspace=True,delimiter="#")
#commit_text= df['comment'].dropna()

print("Data shape:", data.shape)


# In[3]:


#Keywords
#X = data[["add","allow","bug","chang","error","fail","fix","implement","improv","issu","method","new","npe","refactor","remov","report","set","support","test","use"]]
X = data.iloc[:,52:72]
#X = data["add","allow"]
y = data["label"]


# In[12]:


#Changes
#X = data[["ADDING_ATTRIBUTE_MODIFIABILITY","ADDING_CLASS_DERIVABILITY","ADDING_METHOD_OVERRIDABILITY","ADDITIONAL_CLASS","ADDITIONAL_FUNCTIONALITY","ADDITIONAL_OBJECT_STATE","ALTERNATIVE_PART_DELETE","ALTERNATIVE_PART_INSERT","ATTRIBUTE_RENAMING","ATTRIBUTE_TYPE_CHANGE","CLASS_RENAMING","COMMENT_DELETE","COMMENT_INSERT","COMMENT_MOVE","COMMENT_UPDATE","CONDITION_EXPRESSION_CHANGE","DECREASING_ACCESSIBILITY_CHANGE","DOC_DELETE","DOC_INSERT","DOC_UPDATE","INCREASING_ACCESSIBILITY_CHANGE","METHOD_RENAMING","PARAMETER_DELETE","PARAMETER_INSERT","PARAMETER_ORDERING_CHANGE","PARAMETER_RENAMING","PARAMETER_TYPE_CHANGE","PARENT_CLASS_CHANGE","PARENT_CLASS_DELETE","PARENT_CLASS_INSERT","PARENT_INTERFACE_CHANGE","PARENT_INTERFACE_DELETE","PARENT_INTERFACE_INSERT","REMOVED_CLASS","REMOVED_FUNCTIONALITY","REMOVED_OBJECT_STATE","REMOVING_ATTRIBUTE_MODIFIABILITY","REMOVING_CLASS_DERIVABILITY","REMOVING_METHOD_OVERRIDABILITY","RETURN_TYPE_CHANGE","RETURN_TYPE_DELETE","RETURN_TYPE_INSERT","STATEMENT_DELETE","STATEMENT_INSERT","STATEMENT_ORDERING_CHANGE","STATEMENT_PARENT_CHANGE","STATEMENT_UPDATE","UNCLASSIFIED_CHANGE"]]
X = data.iloc[:,4:52]
#X = data["add","allow"]
y = data["label"]


# In[4]:


#Combined
#X = data[["ADDING_ATTRIBUTE_MODIFIABILITY","ADDING_CLASS_DERIVABILITY","ADDING_METHOD_OVERRIDABILITY","ADDITIONAL_CLASS","ADDITIONAL_FUNCTIONALITY","ADDITIONAL_OBJECT_STATE","ALTERNATIVE_PART_DELETE","ALTERNATIVE_PART_INSERT","ATTRIBUTE_RENAMING","ATTRIBUTE_TYPE_CHANGE","CLASS_RENAMING","COMMENT_DELETE","COMMENT_INSERT","COMMENT_MOVE","COMMENT_UPDATE","CONDITION_EXPRESSION_CHANGE","DECREASING_ACCESSIBILITY_CHANGE","DOC_DELETE","DOC_INSERT","DOC_UPDATE","INCREASING_ACCESSIBILITY_CHANGE","METHOD_RENAMING","PARAMETER_DELETE","PARAMETER_INSERT","PARAMETER_ORDERING_CHANGE","PARAMETER_RENAMING","PARAMETER_TYPE_CHANGE","PARENT_CLASS_CHANGE","PARENT_CLASS_DELETE","PARENT_CLASS_INSERT","PARENT_INTERFACE_CHANGE","PARENT_INTERFACE_DELETE","PARENT_INTERFACE_INSERT","REMOVED_CLASS","REMOVED_FUNCTIONALITY","REMOVED_OBJECT_STATE","REMOVING_ATTRIBUTE_MODIFIABILITY","REMOVING_CLASS_DERIVABILITY","REMOVING_METHOD_OVERRIDABILITY","RETURN_TYPE_CHANGE","RETURN_TYPE_DELETE","RETURN_TYPE_INSERT","STATEMENT_DELETE","STATEMENT_INSERT","STATEMENT_ORDERING_CHANGE","STATEMENT_PARENT_CHANGE","STATEMENT_UPDATE","UNCLASSIFIED_CHANGE","add","allow","bug","chang","error","fail","fix","implement","improv","issu","method","new","npe","refactor","remov","report","set","support","test","use"]]
X = data.iloc[:,4:72]
#X = data["add","allow"]
y = data["label"]


# In[5]:


X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.15, random_state = 42)
print(X_train.shape, X_test.shape, y_train.shape, y_test.shape)


# In[6]:


#Random Forest
rfc = RandomForestClassifier()

rfc.fit(X_train,y_train)
# predictions

#rf_pred_train = rfc.predict(X_train)
rf_pred_test = rfc.predict(X_test)
accuracy = metrics.accuracy_score(y_test, rf_pred_test)
#cross-validation
print ('Cross-validation: %.3f' % np.mean(cross_val_score(rfc, X_train, y_train, cv=5)*100))
#kappa
cohen_score = cohen_kappa_score(y_test, rf_pred_test)
print('Kappa: %.3f' % (cohen_score*100))


# In[7]:


#Confusion Matrix
matrix = confusion_matrix(y_test, rf_pred_test)
matrix


# In[8]:


report = classification_report(y_test, rf_pred_test)
print(report)


# In[9]:


precision,recall,fscore,support=score(y_test, rf_pred_test)
print ('Precision : {}'.format(precision*100))
print ('Recall    : {}'.format(recall*100))


# In[13]:


#GBM
model = GradientBoostingClassifier()
model.fit(X_train, y_train)
model_pred_test = model.predict(X_test)
accuracy = metrics.accuracy_score(y_test, model_pred_test)
#print('Accuracy: %.3f' % (accuracy*100))
cohen_score = cohen_kappa_score(y_test, model_pred_test)
print('Kappa: %.3f' % (cohen_score*100))
cv = RepeatedStratifiedKFold(n_splits=10, n_repeats=5, random_state=1)
print ('Cross-validation: %.3f' % np.mean(cross_val_score(rfc, X_train, y_train, cv=5)*100))
#print('Accuracy: %.3f' % (mean(accuracy)*100))


# In[14]:


#J48
clf = tree.DecisionTreeClassifier()
clf.fit(X_train, y_train)
clf_pred_train = clf.predict(X_train)
clf_pred_test = clf.predict(X_test)
accuracy = metrics.accuracy_score(y_test, clf_pred_test)
#print('Accuracy: %.3f' % (accuracy*100))
cohen_score = cohen_kappa_score(y_test, clf_pred_test)
print('Kappa: %.3f' % (cohen_score*100))
print ('Cross-validation: %.3f' % np.mean(cross_val_score(clf, X_train, y_train, cv=5)*100))


# In[ ]:





# In[ ]:




