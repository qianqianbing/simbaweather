package com.simba.service.callbacks;

import com.simba.service.data.DataWrapper;

interface IServiceDataCallback {

     void onChange(in DataWrapper dataWrapper);

      boolean accept(int dataCode);

}