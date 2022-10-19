package com.bishal.coursemaster.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.models.QuestionModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private final List<QuestionModel> itemList;
    private final Context context;
    private ItemClickListener itemClickListener;

    // Constructor of the class
    public QuestionAdapter(List<QuestionModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // determine which layout to use for the row
    @Override
    public int getItemViewType(int position) {
        QuestionModel questionModel = itemList.get(position);
        if (questionModel.getType() == 1) {
            return 1;
        } else if (questionModel.getType() == 2) {
            return 2;
        } else if (questionModel.getType() == 3) {
            return 3;
        } else {
            return -1;
        }
    }

    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class1, parent, false);
            return new ViewHolderOne(view);
        } else if (viewType == TYPE_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class2, parent, false);
            return new ViewHolderTwo(view);
        } else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                initLayoutOne((ViewHolderOne) holder, listPosition);
                break;
            case TYPE_TWO:
                initLayoutTwo((ViewHolderTwo) holder, listPosition);
                break;
            default:
                break;
        }
    }

    public void addItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    private void initLayoutTwo(ViewHolderTwo holder, int listPosition) {

        String question = itemList.get(listPosition).getQuestion();
        String[] arr = question.split("@", 3);
        holder.iquestion.setText(arr[0]);
        holder.c2qb.setText(arr[1]);
        holder.c2qc.setText(arr[2]);
        Glide.with(context).load(itemList.get(listPosition
        ).getA()).into(holder.ioa);
        Glide.with(context).load(itemList.get(listPosition
        ).getB()).into(holder.iob);
        Glide.with(context).load(itemList.get(listPosition
        ).getC()).into(holder.ioc);
        Glide.with(context).load(itemList.get(listPosition
        ).getD()).into(holder.iod);
        String ans = itemList.get(listPosition).getAnswer();
        holder.ioa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    if (ans.equals(itemList.get(listPosition
                    ).getA())) {
                        holder.ioa.setImageResource(R.drawable.correct);
                        itemClickListener.onItemClick(listPosition, true, true);
                    } else {
                        holder.ioa.setImageResource(R.drawable.wrong);
                        itemClickListener.onItemClick(listPosition, true, false);
                    }
                }

            }
        });
        holder.iob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    if (ans.equals(itemList.get(listPosition
                    ).getB())) {
                        holder.iob.setImageResource(R.drawable.correct);
                        itemClickListener.onItemClick(listPosition, true, true);
                    } else {
                        holder.iob.setImageResource(R.drawable.wrong);
                        itemClickListener.onItemClick(listPosition, true, false);
                    }
                }

            }
        });
        holder.ioc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    if (ans.equals(itemList.get(listPosition
                    ).getC())) {
                        holder.ioc.setImageResource(R.drawable.correct);
                        itemClickListener.onItemClick(listPosition, true, true);
                    } else {
                        holder.ioc.setImageResource(R.drawable.wrong);
                        itemClickListener.onItemClick(listPosition, true, false);
                    }
                }

            }
        });
        holder.iod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    if (ans.equals(itemList.get(listPosition
                    ).getD())) {
                        holder.iod.setImageResource(R.drawable.correct);
                        itemClickListener.onItemClick(listPosition, true, true);
                    } else {
                        holder.iod.setImageResource(R.drawable.wrong);
                        itemClickListener.onItemClick(listPosition, true, false);
                    }
                }

            }
        });

    }

    private void initLayoutOne(ViewHolderOne holder, int listPosition) {


        String question = itemList.get(listPosition).getQuestion();
        String[] arr = question.split("@", 3);
        holder.question.setText(arr[0]);
        holder.c1q2.setText(arr[1]);
        holder.c1q3.setText(arr[2]);
        holder.oa.setText(itemList.get(listPosition).getA());
        holder.ob.setText(itemList.get(listPosition).getB());
        holder.oc.setText(itemList.get(listPosition).getC());
        holder.od.setText(itemList.get(listPosition).getD());
        String ans = itemList.get(listPosition).getAnswer();
        holder.oa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (itemClickListener != null) {
                    if (ans.contentEquals(holder.oa.getText())) {
                        holder.oa.setBackgroundColor(Color.parseColor("#16c48A"));
                        itemClickListener.onItemClick(listPosition, true, true);
                    } else {
                        holder.oa.setBackgroundColor(Color.RED);
                        itemClickListener.onItemClick(listPosition, true, false);
                    }

                }

            }
        });
        holder.ob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    if (ans.equals(holder.ob.getText())) {
                        holder.ob.setBackgroundColor(Color.parseColor("#16c48A"));
                        itemClickListener.onItemClick(listPosition, true, true);
                    } else {
                        holder.ob.setBackgroundColor(Color.RED);
                        itemClickListener.onItemClick(listPosition, true, false);
                    }
                }

            }
        });
        holder.oc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    if (ans.contentEquals(holder.oc.getText())) {
                        holder.oc.setBackgroundColor(Color.parseColor("#16c48A"));
                        itemClickListener.onItemClick(listPosition, true, true);
                    } else {
                        holder.oc.setBackgroundColor(Color.RED);
                        itemClickListener.onItemClick(listPosition, true, false);
                    }
                }

            }
        });
        holder.od.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    if (ans.contentEquals(holder.od.getText())) {
                        holder.od.setBackgroundColor(Color.parseColor("#16c48A"));
                        itemClickListener.onItemClick(listPosition, true, true);
                    } else {
                        holder.od.setBackgroundColor(Color.RED);
                        itemClickListener.onItemClick(listPosition, true, false);
                    }
                }

            }
        });
    }

    public interface ItemClickListener {
        void onItemClick(int position, boolean stat, boolean ans);
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView question, oa, ob, oc, od, c1q2, c1q3;

        public ViewHolderOne(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.c1q);
            oa = itemView.findViewById(R.id.c1a);
            ob = itemView.findViewById(R.id.c1b);
            oc = itemView.findViewById(R.id.c1c);
            od = itemView.findViewById(R.id.c1d);
            c1q2 = itemView.findViewById(R.id.c1qb);
            c1q3 = itemView.findViewById(R.id.c1qc);


        }
    }

    static class ViewHolderTwo extends RecyclerView.ViewHolder {
        public TextView iquestion, c2qb, c2qc;
        ImageView ioa, iob, ioc, iod;


        public ViewHolderTwo(View itemView) {
            super(itemView);
            iquestion = itemView.findViewById(R.id.c2q);
            ioa = itemView.findViewById(R.id.c2aiv);
            iob = itemView.findViewById(R.id.c2biv);
            ioc = itemView.findViewById(R.id.c2civ);
            iod = itemView.findViewById(R.id.c2div);
            c2qb = itemView.findViewById(R.id.c2qb);
            c2qc = itemView.findViewById(R.id.c2qc);


        }
    }


}
