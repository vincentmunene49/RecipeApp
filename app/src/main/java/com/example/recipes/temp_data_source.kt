package com.example.recipes

object temp_data_source {

     fun getData():ArrayList<data_source>{
         val data_holder = ArrayList<data_source>()

         val view1 = data_source(R.drawable.donut,false,"donut")
         data_holder.add(view1)
         val view2 = data_source(R.drawable.pizza,false,"pizza")
         data_holder.add(view2)
         val view3 = data_source(R.drawable.hotdog,false,"hotdog")
         data_holder.add(view3)
         val view4 = data_source(R.drawable.yoghurt,false,"yoghurt")
         data_holder.add(view4)
         val view5 = data_source(R.drawable.donut,false,"donut")
         data_holder.add(view5)
         val view6 = data_source(R.drawable.pizza,false,"pizza")
         data_holder.add(view6)
         val view7 = data_source(R.drawable.hotdog,false,"hotdog")
         data_holder.add(view7)
         val view8 = data_source(R.drawable.yoghurt,false,"yoghurt")
         data_holder.add(view8)


         return data_holder
     }


}
