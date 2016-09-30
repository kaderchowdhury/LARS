# -*- coding: utf-8 -*-
"""
Created on Sat Aug 13 12:23:50 2016

@author: kaderchowdhury
"""


from __future__ import print_function

import os
import subprocess

import pandas as pd
import numpy as np
from sklearn.tree import DecisionTreeClassifier, export_graphviz
from sklearn.feature_extraction import DictVectorizer
from sklearn.externals.six import StringIO 
#import pydot
from sklearn import tree
from IPython.display import Image 
#from graph_tool.all import *
from sklearn.ensemble import RandomForestClassifier
# Let's make it available in our namespace:
from sklearn.ensemble import RandomForestRegressor
from multiprocessing import dummy as __mp_dummy
import pydot
from sklearn import cross_validation
from sklearn.metrics import confusion_matrix,classification_report
names = ['id','day', 'time', 'friends', 'activity','feelings','emotion','class'] 
url = "/Users/kaderchowdhury/University/Project/data_v2/datamodel_encoded_v2.csv"

dataframe = pd.read_csv(url, names=names)
array = dataframe.values
print(array)
X = array[:,0:7]
Y = array[:,7]
test_size = 0.33
seed = 7
X_train, X_test, Y_train, Y_test = cross_validation.train_test_split(X, Y, test_size=test_size, random_state=seed)

model = RandomForestClassifier(n_estimators = 30, max_depth=None, min_samples_split=1,random_state=0)
model.fit(X_train, Y_train)
predicted = model.predict(X_test)
matrix = confusion_matrix(Y_test, predicted)
print(matrix)
print(classification_report(Y_test,predicted))
